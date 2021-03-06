# Nexmo Grails Plugin

This plugin gives any Grails application the ability to send SMS messages and outgoing phone calls with text-to-speech using [Nexmo's API](https://www.nexmo.com/).

## Installation

You can add this plugin to your application by adding the following to your `BuildConfig.groovy file`:

```groovy
plugins {
  compile ":nexmo:1.0" // Add this line
}
```

## Methods

#### [__sendSms(String to, String text, String from)__](https://github.com/caseyscarborough/nexmo/blob/master/grails-app/services/grails/plugin/nexmo/NexmoService.groovy#L30)

This method allows you to send an SMS message to a mobile number.

* Parameters
  * __to__ - The mobile number in international format
  * __text__ - Body of the text message (with a maximum length of 3200 characters)
  * __from__ (optional) - The number to send from, defaults to the `default_from` number in [`NexmoConfig.groovy`](https://github.com/caseyscarborough/nexmo/blob/master/grails-app/conf/NexmoConfig.groovy).
* Returns
  * __status__ - The status code of the message
  * __id__ - The ID of the message

#### [__call(String to, String text, String from)__](https://github.com/caseyscarborough/nexmo/blob/master/grails-app/services/grails/plugin/nexmo/NexmoService.groovy#L67)

This method uses text-to-speech to call your recipient and deliver a message.

* Parameters
  * __to__ - The phone number to send the call to, in International Format
  * __text__ - The message to deliver during the call
  * __from__ (optional) - The number to send the call from. Must be a voice enabled inbound number associated with your account
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
    default_from = "15005551234" // Your default from telephone number for SMS
  }
}
```

You can then proceed to use the plugin in a Grails controller or service by injecting the NexmoService bean, and calling the `sendSms` method.

### Example

```groovy
class ExampleController {

  // Inject the service
  def nexmoService

  def index() {
    def smsResult
    def callResult

    try {
      // Send the message "What's up?" to 1-500-123-4567
      smsResult  = nexmoService.sendSms("15001234567", "What's up?")

      // Call the number and tell them a message
      callResult = nexmoService.call("15001234567", "Have a great day! Goodbye.")

    catch (NexmoException e) {
      // Handle error if failure
    }
  }
}
```

Here is an example response:

```groovy
// SMS
[ status: "0", id: "00000125" ]

// Call
[ status: "0", id: "14b75f2246e7c1a17d345449a20d93e5" ]
```
