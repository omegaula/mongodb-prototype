 $(document).ready(function() {
    $("form.validated").submit(function(e){
        var self = e.target;
        $(self).find(".validate").each(function(index){
            if ($(this).hasClass("number")) {
                if (isNaN(this.value)) {
                    $(this).prev("label").addClass("errorMessage");
                } else {
                    $(this).prev("label").removeClass("errorMessage");
                }
            }
        });
        if ($(self).find(".errorMessage").length > 0) {
            return false;
        }
        return true;
    });
 });
