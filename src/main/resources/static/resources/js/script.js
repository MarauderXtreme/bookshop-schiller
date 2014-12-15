$(document).ready(function(){
	$(document).foundation({
		abide : {
			patterns: {
				custom_password: /((?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[_\W]).{8,32})/,
				plz: /^(\d){5}$/,
				custom_price: /^EUR\s[1-9]{1}[0-9]*\.[0-9]{2}$/
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
});