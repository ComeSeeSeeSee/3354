var editorD;

$(function () {

    const E = window.wangEditor;
    editorD = new E('#wangEditor')
    editorD.config.height = 750
    editorD.config.uploadImgServer = '/admin/upload/files'
    editorD.config.uploadFileName = 'files'
    editorD.config.uploadImgMaxSize = 2 * 1024 * 1024
    editorD.config.uploadImgMaxLength = 5
    editorD.config.showLinkImg = false
    editorD.config.uploadImgHooks = {
        success: function (xhr) {
            console.log('success', xhr)
        },
        fail: function (xhr, editor, resData) {
            console.log('fail', resData)
        },
        error: function (xhr, editor, resData) {
            console.log('error', xhr, resData)
        },
        timeout: function (xhr) {
            console.log('timeout')
        },
        customInsert: function (insertImgFn, result) {
            if (result != null && result.resultCode == 200) {

                result.data.forEach(img => {
                    insertImgFn(img)
                });
            } else {
                alert("error");
            }
        }
    }
    editorD.create();


    new AjaxUpload('#uploadGoodsCoverImg', {
        action: '/admin/upload/file',
        name: 'file',
        autoSubmit: true,
        responseType: "json",
        onSubmit: function (file, extension) {
            if (!(extension && /^(jpg|jpeg|png|gif)$/.test(extension.toLowerCase()))) {
                alert('Only jpg, png, gif format files are supported!');
                return false;
            }
        },
        onComplete: function (file, r) {
            if (r != null && r.resultCode == 200) {
                $("#goodsCoverImg").attr("src", r.data);
                $("#goodsCoverImg").attr("style", "width: 128px;height: 128px;display:block;");
                return false;
            } else {
                alert("error");
            }
        }
    });
});

$('#saveButton').click(function () {
    var goodsId = $('#goodsId').val();
    var goodsCategoryId = $('#levelThree option:selected').val();
    var goodsName = $('#goodsName').val();
    var tag = $('#tag').val();
    var originalPrice = $('#originalPrice').val();
    var sellingPrice = $('#sellingPrice').val();
    var goodsIntro = $('#goodsIntro').val();
    var stockNum = $('#stockNum').val();
    var goodsSellStatus = $("input[name='goodsSellStatus']:checked").val();
    var goodsDetailContent = editorD.txt.html();
    var goodsCoverImg = $('#goodsCoverImg')[0].src;
    if (isNull(goodsCategoryId)) {
        swal("Please select a category", {
            icon: "error",
        });
        return;
    }
    if (isNull(goodsName)) {
        swal("Please enter the product name", {
            icon: "error",
        });
        return;
    }
    if (!validLength(goodsName, 100)) {
        swal("Please enter the product name", {
            icon: "error",
        });
        return;
    }
    if (isNull(tag)) {
        swal("Please enter the label of the product", {
            icon: "error",
        });
        return;
    }
    if (!validLength(tag, 100)) {
        swal("Label is too long", {
            icon: "error",
        });
        return;
    }
    if (isNull(goodsIntro)) {
        swal("Please enter product description", {
            icon: "error",
        });
        return;
    }
    if (!validLength(goodsIntro, 100)) {
        swal("Description is too long", {
            icon: "error",
        });
        return;
    }
    if (isNull(originalPrice) || originalPrice < 1) {
        swal("Please enter the price of the product", {
            icon: "error",
        });
        return;
    }
    if (isNull(sellingPrice) || sellingPrice < 1) {
        swal("Please enter the selling price of the product", {
            icon: "error",
        });
        return;
    }
    if (isNull(stockNum) || sellingPrice < 0) {
        swal("Please enter the number of items in stock", {
            icon: "error",
        });
        return;
    }
    if (isNull(goodsSellStatus)) {
        swal("Please select the status", {
            icon: "error",
        });
        return;
    }
    if (isNull(goodsDetailContent)) {
        swal("Please enter the product detail", {
            icon: "error",
        });
        return;
    }
    if (!validLength(goodsDetailContent, 50000)) {
        swal("Product detail is too long", {
            icon: "error",
        });
        return;
    }
    if (isNull(goodsCoverImg) || goodsCoverImg.indexOf('img-upload') != -1) {
        swal("The cover picture cannot be empty", {
            icon: "error",
        });
        return;
    }
    var url = '/admin/goods/save';
    var swlMessage = 'Save successfully';
    var data = {
        "goodsName": goodsName,
        "goodsIntro": goodsIntro,
        "goodsCategoryId": goodsCategoryId,
        "tag": tag,
        "originalPrice": originalPrice,
        "sellingPrice": sellingPrice,
        "stockNum": stockNum,
        "goodsDetailContent": goodsDetailContent,
        "goodsCoverImg": goodsCoverImg,
        "goodsCarousel": goodsCoverImg,
        "goodsSellStatus": goodsSellStatus
    };
    if (goodsId > 0) {
        url = '/admin/goods/update';
        swlMessage = 'Modified successfully';
        data = {
            "goodsId": goodsId,
            "goodsName": goodsName,
            "goodsIntro": goodsIntro,
            "goodsCategoryId": goodsCategoryId,
            "tag": tag,
            "originalPrice": originalPrice,
            "sellingPrice": sellingPrice,
            "stockNum": stockNum,
            "goodsDetailContent": goodsDetailContent,
            "goodsCoverImg": goodsCoverImg,
            "goodsCarousel": goodsCoverImg,
            "goodsSellStatus": goodsSellStatus
        };
    }
    console.log(data);
    $.ajax({
        type: 'POST',//方法类型
        url: url,
        contentType: 'application/json',
        data: JSON.stringify(data),
        success: function (result) {
            if (result.resultCode == 200) {
                swal({
                    title: swlMessage,
                    type: 'success',
                    showCancelButton: false,
                    confirmButtonColor: '#1baeae',
                    confirmButtonText: 'Back to product list',
                    confirmButtonClass: 'btn btn-success',
                    buttonsStyling: false
                }).then(function () {
                    window.location.href = "/admin/goods";
                })
            } else {
                swal(result.message, {
                    icon: "error",
                });
            }
            ;
        },
        error: function () {
            swal("Operation failure", {
                icon: "error",
            });
        }
    });
});

$('#cancelButton').click(function () {
    window.location.href = "/admin/goods";
});

$('#levelOne').on('change', function () {
    $.ajax({
        url: '/admin/categories/listForSelect?categoryId=' + $(this).val(),
        type: 'GET',
        success: function (result) {
            if (result.resultCode == 200) {
                var levelTwoSelect = '';
                var secondLevelCategories = result.data.secondLevelCategories;
                var length2 = secondLevelCategories.length;
                for (var i = 0; i < length2; i++) {
                    levelTwoSelect += '<option value=\"' + secondLevelCategories[i].categoryId + '\">' + secondLevelCategories[i].categoryName + '</option>';
                }
                $('#levelTwo').html(levelTwoSelect);
                var levelThreeSelect = '';
                var thirdLevelCategories = result.data.thirdLevelCategories;
                var length3 = thirdLevelCategories.length;
                for (var i = 0; i < length3; i++) {
                    levelThreeSelect += '<option value=\"' + thirdLevelCategories[i].categoryId + '\">' + thirdLevelCategories[i].categoryName + '</option>';
                }
                $('#levelThree').html(levelThreeSelect);
            } else {
                swal(result.message, {
                    icon: "error",
                });
            }
            ;
        },
        error: function () {
            swal("Operation failure", {
                icon: "error",
            });
        }
    });
});

$('#levelTwo').on('change', function () {
    $.ajax({
        url: '/admin/categories/listForSelect?categoryId=' + $(this).val(),
        type: 'GET',
        success: function (result) {
            if (result.resultCode == 200) {
                var levelThreeSelect = '';
                var thirdLevelCategories = result.data.thirdLevelCategories;
                var length = thirdLevelCategories.length;
                for (var i = 0; i < length; i++) {
                    levelThreeSelect += '<option value=\"' + thirdLevelCategories[i].categoryId + '\">' + thirdLevelCategories[i].categoryName + '</option>';
                }
                $('#levelThree').html(levelThreeSelect);
            } else {
                swal(result.message, {
                    icon: "error",
                });
            }
            ;
        },
        error: function () {
            swal("Operation failure", {
                icon: "error",
            });
        }
    });
});
