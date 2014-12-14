$(document).ready(function(){
	$(document).foundation({
		abide : {
			patterns: {
				custom_password: /((?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[_\W]).{8,32})/,
				plz: /^(\d){5}$/
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