# Nexmo Grails Plugin

This plugin gives any Grails application the ability to send SMS messages using [Nexmo's API](https://www.nexmo.com/).

## Usage

Set the configuration in your `Config.groovy` file, as shown below:

```groovy
nexmo {
  api {
    key    = "abcde123" // Your Nexmo API Key
    secret = "fghij456" // Your Nexmo API Secret
  }

  sms {
    default_from = "15005551234" // Your default from telephone number
  }
}
```

You can then proceed to use the plugin in a Grails controller or service by injecting the NexmoService bean, and calling the `sendSms` method.

```groovy
import grails.converters.JSON

class ExampleController {

  // Inject the service
  def nexmoService

  def index() {
    // Send the message "What up!" to 1-500-123-4567
    def result = nexmoService.sendSms("15001234567", "What up!")
    render result as JSON
  }
}
```

The `sendSms` method returns a Groovy map containing the following information:

* __message-count__: The number of messages sent
* __messages__
  * __to__: The number the message was sent to
  * __message-id__: The ID of the message
  * __status__: The status code of the result
  * __remaining-balance__: The remaining balance on the Nexmo account
  * __message-price__: The cost of the message

Here is an example response:

```groovy
[
  "message-count": "1",
  "messages": [
    [
      "to": "15005551234",
      "message-id": "020000002C64ADA0",
      "status": "0",
      "remaining-balance": "1.98560000",
      "message-price": "0.00480000"
    ]
  ]
]
```