var storage = firebase.storage();
var storageRef = storage.ref();
var currentUser = $("#currentUser").val();
var currentBar = $("#currentBar").val();

$(document).ready(function() {
    $("#drinkImage").on('change', function (e) {
        var file = $(e.target)[0];

        if(file.files.length > 0) {
            picture = file.files[0];
            var date = new Date();
            var timestamp = date.getTime();
            var metadata = {
                contentType: 'image/jpeg'
            };

            var uploadTask = storageRef.child('Images/').child("Drinks/").child(currentUser + timestamp).put(picture, metadata);

            uploadTask.on(firebase.storage.TaskEvent.STATE_CHANGED,
                function(snapshot) {
                    var progress = (snapshot.bytesTransferred / snapshot.totalBytes) * 100;
                    console.log('Upload is ' + progress + '% done');
                }, function(error) {
                    switch (error.code) {
                        case 'storage/unauthorized':
                            alert("User doesn't have permission to access the object");
                            break;

                        case 'storage/canceled':
                            alert("User canceled the upload");
                            break;

                        case 'storage/unknown':
                            alert("Unknown error occurred");
                            break;
                    }
                }, function() {
                    uploadTask.snapshot.ref.getDownloadURL().then(function(downloadURL) {
                        $("#drinkImageUrl").val(downloadURL)
                    });
                });
        }
    });

    $(".drink-edit-btn").on('click', function(e) {
        var drinkId = $(e.target).attr('id').split('-editBtn')[0];
        if (drinkId) {
            $('#editDrinkId').val(drinkId);
            $('#drinkName').val($('#'+drinkId+'-name').text());
            $('#drinkAmount').val($('#'+drinkId+'-amount').text());
            $('#drinkPrice').val($('#'+drinkId+'-price').text());
            $('#drinkVolume').val($('#'+drinkId+'-alcVolume').text());
            $('#drinkDescription').val($('#'+drinkId+'-description').text());
            $('#barOptions').val(currentBar);
            $('#addDrinkModal').modal();
        }
    });

    $('#addDrinkForm').on('submit', function(e) {
        e.preventDefault();
        var name = $('#drinkName');
        var amount = $('#drinkAmount');
        var price = $('#drinkPrice');
        var volume = $('#drinkVolume');
        var bar = $('#barOptions');
        var imageUrl = $('#drinkImageUrl');
        var description = $('#drinkDescription');
        var isEdit = $('#editDrinkId').val();
        var url = '/drink/create';
        var params = {
            name: name.val(),
            amount: amount.val(),
            price: price.val(),
            volume: volume.val(),
            barId: bar.val(),
            imageUrl: imageUrl.val(),
            description: description.val()
        };
        if (isEdit) {
            url = '/drink/edit';
            params.editId = isEdit;
        }
        $.ajax({
            url: url,
            method: 'POST',
            data: params,
            success: function(response) {
                $('#addDrinkModal').modal('hide');
                window.location.reload();
            }
        })
    });
    $("#addDrinkModal").on('hide.bs.modal', function(e) {
        $('#drinkName').val('');
        $('#drinkAmount').val('');
        $('#drinkPrice').val('');
        $('#drinkVolume').val('');
        $('#barOptions').val('');
        $('#drinkImageUrl').val('');
        $('#drinkDescription').val('');
        $('#editDrinkId').val('');
    });
});