
package com.servinow.android.payment;

import android.content.Intent;

public interface IPaymentCallback {
	void onPaymentSuccesful(Payment.Method method);
	void onPaymentProcess(Payment.Method method);
	void onPaymentCanceled(Payment.Method method);
	void onPaymentFailure(Payment.Method method);
}
