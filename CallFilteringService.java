package com.vladlee.easyblacklist;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.telecom.Call.Details;
import android.telecom.CallScreeningService;
import android.telecom.CallScreeningService.CallResponse.Builder;

public class CallFilteringService
  extends CallScreeningService
{
  public IBinder onBind(Intent paramIntent)
  {
    return super.onBind(paramIntent);
  }
  
  public void onScreenCall(Call.Details paramDetails)
  {
    Object localObject = (Uri)paramDetails.getIntentExtras().getParcelable("android.telecom.extra.INCOMING_CALL_ADDRESS");
    if (localObject != null) {
      localObject = Uri.decode(((Uri)localObject).getSchemeSpecificPart());
    } else {
      localObject = null;
    }
    if (aw.a(this, (String)localObject))
    {
      CallScreeningService.CallResponse.Builder localBuilder = new CallScreeningService.CallResponse.Builder();
      localBuilder.setDisallowCall(true);
      localBuilder.setRejectCall(true);
      localBuilder.setSkipCallLog(true);
      localBuilder.setSkipNotification(true);
      respondToCall(paramDetails, localBuilder.build());
      paramDetails = new Intent(this, CallBlockHandlerDefPhoneApp.class);
      paramDetails.putExtra(ea.a, (String)localObject);
      startService(paramDetails);
      return;
    }
    localObject = new CallScreeningService.CallResponse.Builder();
    ((CallScreeningService.CallResponse.Builder)localObject).setDisallowCall(false);
    ((CallScreeningService.CallResponse.Builder)localObject).setRejectCall(false);
    ((CallScreeningService.CallResponse.Builder)localObject).setSkipCallLog(false);
    ((CallScreeningService.CallResponse.Builder)localObject).setSkipNotification(false);
    respondToCall(paramDetails, ((CallScreeningService.CallResponse.Builder)localObject).build());
  }
}
