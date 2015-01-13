$(document).ready(function(){
	$(document).foundation({
		abide : {
			patterns: {
				custom_password: /((?=.*\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[_\W]).{8,32})/,
				plz: /^(\d){5}$/,
				custom_price: /^([1-9]{1}\d*|0)\.\d{2}$/,
				isbn13: /^[0-9]{13}$/,
				caps_start: /^[A-Z](1).*/,
				custom_date: /^(0[1-9]|[12]\d|3[0-1])\.(0[1-9]|1[0-2])\.\d{4}$/,
				custom_time: /^(0[1-9]|1\d|2[0-3]):([0-5]\d)$/,
				image: /^([0-9a-zA-Z\-\_]*\.(jpg|jpeg|png|gif|JPG|JPEG|PNG|GIF))$/
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

	$('#datepicker').fdatepicker({
		format: 'dd.mm.yyyy',
		language: 'de'
	})
		
	var sellStats = parseFloat($('#sell-stats').text().substring(4));
	var buyStats = parseFloat($('#buy-stats').text().substring(4));
	var profitStats = parseFloat($('#profit-stats').text().substring(4));
	
	var totalSellStats = parseFloat($('#total-sell-stats').text().substring(4));
	var totalBuyStats = parseFloat($('#total-buy-stats').text().substring(4));
	var totalProfitStats = parseFloat($('#total-profit-stats').text().substring(4));
		
	$('#complete-stats').highcharts({
		chart: {
			type: 'column'
		},
		title: {
			text: 'Übersicht'
		},
		xAxis: {
			categories: ['Einnahmen', 'Ausgaben', 'Profit']
		},
		yAxis: {
			title: {
				text: '€'
			}
		},
		series: [{
			name: 'Woche',
			data: [buyStats, sellStats, profitStats]
		},
		{
			name: 'Gesamt',
			data: [totalBuyStats, totalSellStats, totalProfitStats]
		}]
	});
	
	$('#plain-statistics').hide();
	
	/**
	 * Spring HTML escapes 
	 */
	//CKEDITOR.replace( 'descriptiontext' );
});
