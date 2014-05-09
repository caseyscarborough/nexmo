package grails.plugin.nexmo

import groovyx.net.http.HTTPBuilder
import org.springframework.context.i18n.LocaleContextHolder

import static groovyx.net.http.ContentType.URLENC
import static groovyx.net.http.Method.POST

class NexmoService {

  def grailsApplication
  def messageSource

  def sendSms(String to, String text, String from=config?.sms?.default_from) throws NexmoException {
    if (!to || !text || !from) {
      log.error("A required parameter was not supplied. Not sending SMS.")
      throw new NexmoException("A required parameter was not supplied. Not sending SMS.")
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
          def error = message?."error-text" ?: getMessage(statusCode, "An error occurred sending the SMS.")
          log.error("An error occurred sending the SMS: ${error}")
          throw new NexmoException(error)
        }
        log.info("SMS was successfully sent.")
        return [status: message?.status, id: message?."message-id"]
      }
      response.failure = { resp, data ->
        log.error("An error occurred sending the SMS.")
        throw new NexmoException("A ${resp?.status} error was encountered.")
      }
    }
  }

  private ConfigObject getConfig() {
    return grailsApplication.config?.nexmo
  }

  private String getMessage(String code, String defaultMessage) {
    if (code) {
      return messageSource.getMessage("nexmo.sms.status.${code}", [].toArray(), defaultMessage, LocaleContextHolder.locale)
    }
    return defaultMessage
  }
}
