class NexmoGrailsPlugin {
  // the plugin version
  def version = "0.1"
  // the version or versions of Grails the plugin is designed for
  def grailsVersion = "2.3 > *"
  // resources that are excluded from plugin packaging
  def pluginExcludes = [ ]

  def title = "Nexmo Plugin" // Headline display name of the plugin
  def author = "Casey Scarborough"
  def authorEmail = "caseyscarborough@gmail.com"
  def description = '''\
The Nexmo Plugin gives applications SMS (Text Messaging) and Voice functionality. It allows an application to receive
and send text messages, as well as sending automated voice calls.
'''

  // URL to the plugin's documentation
  def documentation = "https://github.com/caseyscarborough/nexmo"
  def license = "APACHE"

  // Location of the plugin's issue tracker.
  def issueManagement = [ system: "GitHub", url: "https://github.com/caseyscarborough/nexmo/issues" ]

  // Online location of the plugin's browseable source code.
  def scm = [ url: "https://github.com/caseyscarborough/nexmo" ]

  def doWithWebDescriptor = { xml ->
    // TODO Implement additions to web.xml (optional), this event occurs before
  }

  def doWithSpring = {
    // TODO Implement runtime spring config (optional)
  }

  def doWithDynamicMethods = { ctx ->
    // TODO Implement registering dynamic methods to classes (optional)
  }

  def doWithApplicationContext = { ctx ->
    // TODO Implement post initialization spring config (optional)
  }

  def onChange = { event ->
    // TODO Implement code that is executed when any artefact that this plugin is
    // watching is modified and reloaded. The event contains: event.source,
    // event.application, event.manager, event.ctx, and event.plugin.
  }

  def onConfigChange = { event ->
    // TODO Implement code that is executed when the project configuration changes.
    // The event is the same as for 'onChange'.
  }

  def onShutdown = { event ->
    // TODO Implement code that is executed when the application shuts down (optional)
  }
}
