$(document).ready(function(){
	$(document).foundation({
		abide : {
			patterns: {
				custom_password: /((?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[_\W]).{8,32})/,
				plz: /^(\d){5}$/,
				custom_price: /^EUR\s[1-9]{1}\d*\.\d{2}$/,
				isbn13: /^[0-9]{13}$/,
				caps_start: /^[A-Z](1).*/,
				custom_date: /^(0[1-9]|[12]\d|3[0-1])\.(0[1-9]|1[0-2])\.\d{4}$/,
				custom_eventdate: /^(0[1-9]|[12]\d|3[0-1])(0[1-9]|1[0-2])\d{4}$/,
				custom_time: /^(0[1-9]|1\d|2[0-3])([0-5]\d)$/
			}
		}
	});
	$('.header-slider').slick({
		dots: true,
		infinite: true,
		lazyLoad: 'ondemand',
		slidesToShow: 1,
		slidesToScroll: 1,
		autoplay: true,
		autoplaySpeed: 10000,
	});
	/**
	 * Spring escapes 
	 */
	//CKEDITOR.replace( 'descriptiontext' );
});