package grails.plugin.nexmo

import groovyx.net.http.HTTPBuilder
import org.springframework.context.i18n.LocaleContextHolder as LCH

import static groovyx.net.http.ContentType.URLENC
import static groovyx.net.http.Method.POST

class NexmoService {

  def grailsApplication
  def messageSource

  def sendSms(String to, String text, String from=config?.sms?.default_from) throws NexmoException {
    if (!to || !text || !from) {
      throw new NexmoException(getMessage("nexmo.sms.error.missing.param"))
    }

    def http = new HTTPBuilder(config?.endpoint)
    def requestBody = [to: to, text: text, from: from, api_key: config?.api?.key, api_secret: config?.api?.secret]

    http.request(POST) {
      uri.path = "/sms/${config?.format}"
      send(URLENC, requestBody)

      response.success = { resp, data ->
        def message = data?.messages[0]
        def statusCode = message?.status
        if (statusCode != "0") {
          def error = getMessage("nexmo.sms.status.${statusCode}", [message?."error-text"], getMessage("nexmo.sms.error.default"))
          throw new NexmoException(error)
        }
        log.info(getMessage("nexmo.sms.success"))
        return [status: message?.status, id: message?."message-id"]
      }
      response.failure = { resp, data ->
        def error = getMessage("nexmo.sms.error.response", [resp?.status], getMessage("nexmo.sms.error.default"))
        throw new NexmoException(error)
      }
    }
  }

  def call(String to, String text, String from="") throws NexmoException {
    if (!to || !text) {
      throw new NexmoException(getMessage("nexmo.call.error.missing.param"))
    }

    def http = new HTTPBuilder(config?.endpoint)
    def requestBody = [to: to, text: text, from: from, api_key: config?.api?.key, api_secret: config?.api?.secret]

    http.request(POST) {
      uri.path = "/tts/${config?.format}"
      send(URLENC, requestBody)

      response.success = { resp, data ->
        def statusCode = data?.status
        if (statusCode != "0") {
          def error = getMessage("nexmo.call.status.${statusCode}", [data?."error-text"], getMessage("nexmo.call.error.default"))
          throw new NexmoException(error)
        }
        log.info(getMessage("nexmo.call.success"))
        return [status: data?.status, id: data?."call-id"]
      }
      response.failure = { resp, data ->
        def error = getMessage("nexmo.call.error.response", [resp?.status], getMessage("nexmo.call.error.default"))
        throw new NexmoException(error)
      }
    }
  }

  private ConfigObject getConfig() {
    return grailsApplication.config?.nexmo
  }

  private String getMessage(String code, List args=[], String defaultMessage="") {
    if (messageSource.resolveCode(code, LCH.locale)) {
      return messageSource.getMessage(code, args.toArray(), LCH.locale)
    }
    return defaultMessage
  }
}
