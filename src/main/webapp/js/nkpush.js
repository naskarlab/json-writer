function nkpushGetDeviceId() {
	if(Android) {
		return Android.hpvGetDeviceId();
	} else {
		return "";
	}
}