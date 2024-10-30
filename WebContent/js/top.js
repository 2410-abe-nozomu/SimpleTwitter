///削除ボタンの確認ダイアログ
$(function(){
    $("#delete").on("click", function() {
    	if(!window.confirm( '本当に削除していいですか？')){
    		return false
    	}
    });
});