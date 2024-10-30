///設定変更時の確認ダイアログ
$(function(){
    $("#setting").on("click", function() {
    	if(!window.confirm( 'アカウント情報を更新します。よろしいですか？')){
    		return false
    	}
    });
});