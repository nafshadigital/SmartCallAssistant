<?php

// Update the path below to your autoload.php,
// see https://getcomposer.org/doc/01-basic-usage.md
//require_once 'twilio-php-master/Twilio/autoload.php';

use Twilio\Rest\Client;

class TwilioAPI {
// Find your Account Sid and Auth Token at twilio.com/console
static $sid    = "AC6f95942c0c6e670ca60dd308c9c04c5f";
static $token  = "7dd0ad3b88b2d469135b27ec02f24721";
static $twilioNumber = "16085301789";

public static function sms($to, $message) {
	if(strlen($message) > 160) {
    	$message = substr($message, 0, 160);
    }
    $twilio = new Client(self::$sid, self::$token);
    
    $message = $twilio->messages
                      ->create($to, // to
                               array("from" => self::$twilioNumber, "body" => $message)
                      );
    return $message;       
}

}
?>
