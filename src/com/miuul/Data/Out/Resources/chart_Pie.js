function CreatePie(){
	var mainChart = echarts.init(document.getElementById('Chart'));
			mainChart.clear();
			var option = {
			    tooltip: {
                    trigger: 'item'
                  },
				series : [
				    {
				        name: '$Name$',
				        type: 'pie', 
				        radius: '55%', 
				        data:[$DataList$],
				        emphasis: {
                                  itemStyle: {
                                  shadowBlur: 10,
                                  shadowOffsetX: 0,
                                  shadowColor: 'rgba(0, 0, 0, 0.5)'
                                  }
                        }
				    }
				]
			};
	        mainChart.setOption(option)
}