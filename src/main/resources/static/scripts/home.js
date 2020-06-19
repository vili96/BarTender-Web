var storage = firebase.storage();
var storageRef = storage.ref();
var currentUser = $("#currentUser").val();

$(document).ready(function(){
    $("#barImage").on('change', function (e) {
        var file = $(e.target)[0];

        if(file.files.length > 0) {
            picture = file.files[0];
            var date = new Date();
            var timestamp = date.getTime();
            var metadata = {
                contentType: 'image/jpeg'
            };

            var uploadTask = storageRef.child('Images/').child("Bars/").child(currentUser + timestamp).put(picture, metadata);

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
                        $("#imageUrl").val(downloadURL)
                    });
                });
        }
    });
    // $("#drinkImage").on('change', function (e) {
    //     var file = $(e.target)[0];
    //
    //     if(file.files.length > 0) {
    //         picture = file.files[0];
    //         var date = new Date();
    //         var timestamp = date.getTime();
    //         var metadata = {
    //             contentType: 'image/jpeg'
    //         };
    //
    //         var uploadTask = storageRef.child('Images/').child("Drinks/").child(currentUser + timestamp).put(picture, metadata);
    //
    //         uploadTask.on(firebase.storage.TaskEvent.STATE_CHANGED,
    //             function(snapshot) {
    //                 var progress = (snapshot.bytesTransferred / snapshot.totalBytes) * 100;
    //                 console.log('Upload is ' + progress + '% done');
    //             }, function(error) {
    //                 switch (error.code) {
    //                     case 'storage/unauthorized':
    //                         alert("User doesn't have permission to access the object");
    //                         break;
    //
    //                     case 'storage/canceled':
    //                         alert("User canceled the upload");
    //                         break;
    //
    //                     case 'storage/unknown':
    //                         alert("Unknown error occurred");
    //                         break;
    //                 }
    //             }, function() {
    //                 uploadTask.snapshot.ref.getDownloadURL().then(function(downloadURL) {
    //                     $("#drinkImageUrl").val(downloadURL)
    //                 });
    //             });
    //     }
    // });
    $('#addBarForm').on('submit', function(e) {
        e.preventDefault();
        var name = $('#barName');
        var user = $('#userOptions');
        var imageUrl = $('#imageUrl');
        var address = $('#barAddress');
        var isEdit = $('#editBarId').val();
        var url = '/bar/create';
        var params = {
            name: name.val(),
            userId: user.val(),
            imageUrl: imageUrl.val(),
            address: address.val()
        };
        if (isEdit) {
            url = '/bar/edit';
            params.editId = isEdit;
        }
        $.ajax({
            url: url,
            method: 'POST',
            data: params,
            success: function(response) {
                $('#addBarModal').modal('hide');
                $("#imageUrl").val('');
                $('#editBarId').val('')
                window.location.reload();
            }
        })
    });
    // $('#addDrinkForm').on('submit', function(e) {
    //     e.preventDefault();
    //     var name = $('#drinkName');
    //     var amount = $('#drinkAmount');
    //     var price = $('#drinkPrice');
    //     var volume = $('#drinkVolume');
    //     var bar = $('#barOptions');
    //     var imageUrl = $('#drinkImageUrl');
    //     var description = $('#drinkDescription');
    //     $.ajax({
    //         url: '/drink/create',
    //         method: 'POST',
    //         data: {
    //             name: name.val(),
    //             amount: amount.val(),
    //             price: price.val(),
    //             volume: volume.val(),
    //             barId: bar.val(),
    //             imageUrl: imageUrl.val(),
    //             description: description.val()
    //         },
    //         success: function(response) {
    //             $('#addDrinkModal').modal('hide');
    //             window.location.reload();
    //         }
    //     })
    // });
    $(".bar-edit-btn").on('click', function(e) {
        var barId = $(e.target).attr('id').split('-editBtn')[0];
        console.log(barId);
        if (barId) {
            $('#editBarId').val(barId);
            var name = $('#barName');
            var user = $('#userOptions');
            // var imageUrl = $('#imageUrl');
            var address = $('#barAddress');
            var userId = $(e.target).closest('div.card').attr('id').split('-')[0];
            $('#editBarId').val(barId);
            name.val($('#'+barId+'-name').text());
            user.val(userId);
            address.val($('#'+barId+'-address').text());
            $('#addBarModal').modal();
        }
    });
});