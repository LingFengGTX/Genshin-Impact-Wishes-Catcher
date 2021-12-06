function list_lostActive(){
	var listElement=document.getElementById("chartsTypeList");
	listElement.setAttribute("style","display: none;");
}

function show_list(){
	var listElement=document.getElementById("chartsTypeList");
	listElement.setAttribute("style","");
}

function ChangeSheet(item,Index){
	var TextContent=document.getElementById("echo_Content");
	var listElement=document.getElementById("chartsTypeList");
	TextContent.innerText=item.innerText;
	switch(Number(Index)){
			case 0:{
				CreatePie();
			};break;
			case 1:{
				CreateBar();
				
			};break;
	}
	
	//强制隐藏列表
	
	listElement.setAttribute("style","display: none;");
	
}