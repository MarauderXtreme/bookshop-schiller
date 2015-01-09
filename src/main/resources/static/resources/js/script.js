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
				custom_eventdate: /^(0[1-9]|[12]\d|3[0-1])\.(0[1-9]|1[0-2])\.\d{4}$/,
				custom_time: /^(0[1-9]|1\d|2[0-3]):([0-5]\d)$/
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
	
	var test = 13.37;
	
	var sellStats = $('#sell-stats').text().substring(4);
	var buyStats = $('#buy-stats').text().substring(4);
	var profitStats = $('#profit-stats').text().substring(4);
	
	var totalSellStats = $('#total-sell-stats').text().substring(4);
	var totalBuyStats = $('#total-buy-stats').text().substring(4);
	var totalProfitStats = $('#total-profit-stats').text().substring(4);
	
	$('#weekly-stats').highcharts({
		chart: {
			type: 'column'
		},
		title: {
			text: 'Wochenübersicht'
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
			data: [sellStats, buyStats, profitStats]
		}]
	});
	
	$('#total-stats').highcharts({
		chart: {
			type: 'column'
		},
		title: {
			text: 'Gesamtübersicht'
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
			data: [totalSellStats, totalBuyStats, totalProfitStats]
		}]
	});
	
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
			data: [sellStats, buyStats, profitStats]
		},
		{
			name: 'Gesamt',
			data: [totalSellStats, totalBuyStats, totalProfitStats]
		}]
	});
	
	$('#plain-statistics').hide();
	
	/**
	 * Spring HTML escapes 
	 */
	//CKEDITOR.replace( 'descriptiontext' );
});
