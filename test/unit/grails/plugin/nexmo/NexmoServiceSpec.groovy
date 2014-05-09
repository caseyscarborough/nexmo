package grails.plugin.nexmo

import grails.test.mixin.TestFor
import spock.lang.Specification

@TestFor(NexmoService)
class NexmoServiceSpec extends Specification {

  def setup() {
    def config = new ConfigSlurper().parse(new File('grails-app/conf/NexmoConfig.groovy').toURL())
    grailsApplication.config.nexmo = config.nexmo
    assert grailsApplication.config.nexmo.test.phone_number
    service.grailsApplication = grailsApplication
  }

  def cleanup() {
  }

  void "Test Successful SMS"() {
    when:
    def result = service.sendSms(grailsApplication.config?.nexmo?.test?.phone_number, "This is a test from Grails!")

    then:
    result?.status == "0"
    result?.id?.length() >= 8
  }
}
