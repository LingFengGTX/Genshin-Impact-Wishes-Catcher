function CreateBar(){
	var myChart = echarts.init(document.getElementById('Chart'));
			myChart.clear();
	        var option = {
	            tooltip: {},
	            legend: {
	                data:['$Name$']
	            },
	            xAxis: {
	                data: [$DataList$]
	            },
	            yAxis: {},
	            series: [{
	                name: '$Name$',
	                type: 'bar',
	                data: [$DataCount$]
	            }]
	        };
	        myChart.setOption(option);
}