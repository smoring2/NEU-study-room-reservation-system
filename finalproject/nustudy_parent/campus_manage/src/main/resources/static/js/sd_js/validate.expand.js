//Custom verification email
jQuery.validator.addMethod("email", function(value, element) {
    var tel = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
    return this.optional(element) || (tel.test(value));
}, "Your email is not in the correct format！");

//Custom phone number verification
jQuery.validator.addMethod("isphoneNum", function(value, element) {
    var length = value.length;
    var mobile = /^1[3|5|8]{1}[0-9]{9}$/;
    return this.optional(element) || (length == 11 && mobile.test(value));
}, "Please fill in your mobile number correctly");

//Custom URL verification
jQuery.validator.addMethod("isURL", function(value, element) {
    var url = /http(s)?:\/\/([\w-]+\.)+[\w-]+(\/[\w- .\/?%&=]*)?/;
    return this.optional(element) || (url.test(value));
}, "The URL you filled in is not in the correct format!");

//Custom drop-down box selection validation
jQuery.validator.addMethod("isSelect", function(value, element) {
    var select = /^[0-9]*$/;
    return this.optional(element) || (select.test(value));
}, "You need to choose the correct option！");

