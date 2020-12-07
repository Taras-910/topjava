var ctx;
filter = $('#filter');

function updateFilteredTable() {
    $.ajax({
        url: ctx.ajaxUrl +"filter",
        type: "GET",
        data: filter.serialize(),
        success: function (data) {
            ctx.datatableApi.clear().rows.add(data).draw();
            successNoty("Filtered");
        }
    }, "json");
}

function clearFilter() {
    updateTable();
}

function save() {
    $.ajax({
        type: "POST",
        url: ctx.ajaxUrl,
        data: form.serialize()
    }).done(function () {
        $("#editRow").modal("hide");
        updateFilteredTable();
        successNoty("Saved");
    });
}

function deleteRow(id) {
    if (confirm('Are you sure?')) {
        $.ajax({
            url: ctx.ajaxUrl + id,
            type: "DELETE"
        }).done(function () {
            updateFilteredTable();
            successNoty("Deleted");
        });
    }
}

// $(document).ready(function () {
$(function () {
    // https://stackoverflow.com/a/5064235/548473
    ctx = {
        ajaxUrl: "profile/meals/",
        datatableApi: $("#datatable").DataTable({
            "paging": false,
            "info": true,
            "columns": [
                {
                    "data": "dateTime"
                },
                {
                    "data": "description"
                },
                {
                    "data": "calories"
                },
                {
                    "defaultContent": "Edit",
                    "orderable": false
                },
                {
                    "defaultContent": "Delete",
                    "orderable": false
                }
            ],
            "order": [
                [
                    0,
                    "desc"
                ]
            ]
        })
    };
    makeEditable();
});
