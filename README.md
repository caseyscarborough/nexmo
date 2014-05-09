# Nexmo Grails Plugin

This plugin gives any Grails application the ability to send SMS messages using [Nexmo's API](https://www.nexmo.com/).

## Methods

#### [__sendSms(String to, String text, String from)__](https://github.com/caseyscarborough/nexmo/blob/master/grails-app/services/grails/plugin/nexmo/NexmoService.groovy#L14)

* Parameters
  * __to__ - The mobile number in international format
  * __text__ - Body of the text message (with a maximum length of 3200 characters)
  * __from__ (optional) - The number to send from, defaults to the `default_from` number in [`NexmoConfig.groovy`](https://github.com/caseyscarborough/nexmo/blob/master/grails-app/conf/NexmoConfig.groovy).
* Returns
  * __status__ - The status code of the message
  * __id__ - The ID of the message

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

### Example

```groovy
import grails.converters.JSON

class ExampleController {

  // Inject the service
  def nexmoService

  def index() {
    def result
    try {
      // Send the message "What up!" to 1-500-123-4567
      result = nexmoService.sendSms("15001234567", "What up!")
    catch (NexmoException e) {
      // Handle error if failure
    }
  }
}
```

Here is an example response:

```groovy
[ status: "0", id: "00000125" ]
```