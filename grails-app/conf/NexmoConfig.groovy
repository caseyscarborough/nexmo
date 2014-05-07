// This file is the configuration for the Nexmo Plugin that is shipped with
// the plugin, and loaded at the time of the plugin.

nexmo {
  endpoint = "https://rest.nexmo.com/"
  format = "json"

  api {
    key = ""
    secret = ""
  }

  test {
    // This must be a verified number in your Nexmo account
    phone_number = ""
  }

  sms {
    default_from = ""
  }
}