
//@Bind #btn.onClick
!function(){
	var voucher_code="";
	  try{
		 voucher_code=view.get("#voucherAutoForm.entity").get("voucher_code");
			
		 }catch(e){
		  voucher_code ="";
	    }
		if(voucher_code.length>0){
		
			view.get("#choseStock").set("caption","选择");
			view.get("#choseStock").show();
		}else
		{
			swal({
				title:"请先新增单据！",
				type:"info",
				confirmButtonColor: "#16987e"	
			});
			//swal("请先新增单据！");
		}

}

//@Bind #new.onClick
!function(){
	swal({
	    title: "确定要新增单据？",
	    type: "info",
	    showCancelButton: true,
	    closeOnConfirm: true,
	    confirmButtonColor: "#16987e",
	    confirmButtonText: "确定",
	    cancelButtonText: "取消"
	},
	function() {
		var data = view.get("#voucherAutoForm.entity");
		with (view.get("#dataSetVoucher")) {
			set("parameter",data);
			flushAsync();
		}
	});
	view.get("#dataSetInstock").flushAsync();
}


//@Bind #view.onReady
!function(){
	init();
} ;