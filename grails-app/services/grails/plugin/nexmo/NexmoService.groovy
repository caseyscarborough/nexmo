package grails.plugin.nexmo

import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.ContentType.URLENC
import static groovyx.net.http.Method.POST

class NexmoService {

  def grailsApplication

  def sendSms(String to, String text, String from=config?.sms?.default_from) {
    if (!from) {
      log.error("A from phone number was not supplied. Not sending SMS.")
      return false
    }

    def http = new HTTPBuilder(config?.endpoint)
    def requestBody = [to: to, text: text, from: from, api_key: config?.api?.key, api_secret: config?.api?.secret]

    log.info("Attempting to send SMS with the following data:\n${requestBody}")
    http.request(POST) {
      uri.path = "/"
      send(URLENC, requestBody)

      response.success = { resp, data ->
        log.info("SMS was successfully sent.\n${data}")
        return data
      }
      response.failure = { resp, data ->
        log.info("An error occurred sending the SMS.\n${data}")
        return data
      }
    }
  }

  private ConfigObject getConfig() {
    return grailsApplication.config?.nexmo
  }
}
