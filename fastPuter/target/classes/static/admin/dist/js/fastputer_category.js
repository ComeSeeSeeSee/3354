$(function () {
    var categoryLevel = $("#categoryLevel").val();
    var parentId = $("#parentId").val();

    $("#jqGrid").jqGrid({
        url: '/admin/categories/list?categoryLevel=' + categoryLevel + '&parentId=' + parentId,
        datatype: "json",
        colModel: [
            {label: 'id', name: 'categoryId', index: 'categoryId', width: 50, key: true, hidden: true},
            {label: 'Category Name', name: 'categoryName', index: 'categoryName', width: 240},
            {label: 'Rank', name: 'categoryRank', index: 'categoryRank', width: 120},
            {label: 'Created time', name: 'createTime', index: 'createTime', width: 120}
        ],
        height: 560,
        rowNum: 10,
        rowList: [10, 20, 50],
        styleUI: 'Bootstrap',
        loadtext: 'Loading text...',
        rownumbers: false,
        rownumWidth: 20,
        autowidth: true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "data.list",
            page: "data.currPage",
            total: "data.totalPage",
            records: "data.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order",
        },
        gridComplete: function () {
            //隐藏grid底部滚动条
            $("#jqGrid").closest(".ui-jqgrid-bdiv").css({"overflow-x": "hidden"});
        }
    });

    $(window).resize(function () {
        $("#jqGrid").setGridWidth($(".card-body").width());
    });
});


function reload() {
    var page = $("#jqGrid").jqGrid('getGridParam', 'page');
    $("#jqGrid").jqGrid('setGridParam', {
        page: page
    }).trigger("reloadGrid");
}

function categoryAdd() {
    reset();
    $('.modal-title').html('Add category');
    $('#categoryModal').modal('show');
}


function categoryManage() {
    var categoryLevel = parseInt($("#categoryLevel").val());
    var parentId = $("#parentId").val();
    var id = getSelectedRow();
    if (id == undefined || id < 0) {
        return false;
    }
    if (categoryLevel == 1 || categoryLevel == 2) {
        categoryLevel = categoryLevel + 1;
        window.location.href = '/admin/categories?categoryLevel=' + categoryLevel + '&parentId=' + id + '&backParentId=' + parentId;
    } else {
        swal("No subordinate classification", {
            icon: "warning",
        });
    }
}


function categoryBack() {
    var categoryLevel = parseInt($("#categoryLevel").val());
    var backParentId = $("#backParentId").val();
    if (categoryLevel == 2 || categoryLevel == 3) {
        categoryLevel = categoryLevel - 1;
        window.location.href = '/admin/categories?categoryLevel=' + categoryLevel + '&parentId=' + backParentId + '&backParentId=0';
    } else {
        swal("No higher classification", {
            icon: "warning",
        });
    }
}


$('#saveButton').click(function () {
    var categoryName = $("#categoryName").val();
    var categoryLevel = $("#categoryLevel").val();
    var parentId = $("#parentId").val();
    var categoryRank = $("#categoryRank").val();
    if (!validCN_ENString2_30(categoryName)) {
        $('#edit-error-msg').css("display", "block");
        $('#edit-error-msg').html("Please enter a category name that meets the specification！");
    } else {
        var data = {
            "categoryName": categoryName,
            "categoryLevel": categoryLevel,
            "parentId": parentId,
            "categoryRank": categoryRank
        };
        var url = '/admin/categories/save';
        var id = getSelectedRowWithoutAlert();
        if (id != null) {
            url = '/admin/categories/update';
            data = {
                "categoryId": id,
                "categoryName": categoryName,
                "categoryLevel": categoryLevel,
                "parentId": parentId,
                "categoryRank": categoryRank
            };
        }
        $.ajax({
            type: 'POST',
            url: url,
            contentType: 'application/json',
            data: JSON.stringify(data),
            success: function (result) {
                if (result.resultCode == 200) {
                    $('#categoryModal').modal('hide');
                    swal("Saved successfully", {
                        icon: "success",
                    });
                    reload();
                } else {
                    $('#categoryModal').modal('hide');
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
    }
});

function categoryEdit() {
    reset();
    var id = getSelectedRow();
    if (id == null) {
        return;
    }
    var rowData = $("#jqGrid").jqGrid("getRowData", id);
    $('.modal-title').html('Category Editor');
    $('#categoryModal').modal('show');
    $("#categoryId").val(id);
    $("#categoryName").val(rowData.categoryName);
    $("#categoryRank").val(rowData.categoryRank);
}


function deleteCagegory() {

    var ids = getSelectedRows();
    if (ids == null) {
        return;
    }
    swal({
        title: "To Confirm",
        text: "Do you want to delete the data?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    }).then((flag) => {
            if (flag) {
                $.ajax({
                    type: "POST",
                    url: "/admin/categories/delete",
                    contentType: "application/json",
                    data: JSON.stringify(ids),
                    success: function (r) {
                        if (r.resultCode == 200) {
                            swal("Delete successfully", {
                                icon: "success",
                            });
                            $("#jqGrid").trigger("reloadGrid");
                        } else {
                            swal(r.message, {
                                icon: "error",
                            });
                        }
                    }
                });
            }
        }
    )
    ;
}


function reset() {
    $("#categoryName").val('');
    $("#categoryRank").val(0);
    $('#edit-error-msg').css("display", "none");
}