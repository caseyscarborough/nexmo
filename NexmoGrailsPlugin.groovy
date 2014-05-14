import grails.util.Environment

class NexmoGrailsPlugin {
  // the plugin version
  def version = "1.0"
  // the version or versions of Grails the plugin is designed for
  def grailsVersion = "2.3 > *"
  // resources that are excluded from plugin packaging
  def pluginExcludes = [ ]

  def title = "Nexmo Plugin" // Headline display name of the plugin
  def author = "Casey Scarborough"
  def authorEmail = "caseyscarborough@gmail.com"
  def description = '''\
The Nexmo Plugin gives Grails applications SMS (Text Messaging) and Voice functionality. It allows an application to receive
and send text messages, as well as sending automated voice calls using Nexmo's API.
'''

  // URL to the plugin's documentation
  def documentation = "https://github.com/caseyscarborough/nexmo"
  def license = "APACHE"

  // Location of the plugin's issue tracker.
  def issueManagement = [ system: "GitHub", url: "https://github.com/caseyscarborough/nexmo/issues" ]

  // Online location of the plugin's browseable source code.
  def scm = [ url: "https://github.com/caseyscarborough/nexmo" ]

  def doWithSpring = {
    mergeConfig(application)
  }

  // Merges the Nexmo Configuration with the application's configuration
  protected mergeConfig(application) {
    application.config.merge(loadConfig(application))
  }

  // Load the configuration from the NexmoConfig.groovy
  protected loadConfig(application) {
    new ConfigSlurper(Environment.current.name).parse(application.classLoader.loadClass("NexmoConfig"))
  }
}
