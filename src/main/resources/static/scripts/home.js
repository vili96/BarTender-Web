var storage = firebase.storage();
var storageRef = storage.ref();
var currentUser = firebase.auth().currentUser;

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
    $('#addBarForm').on('submit', function(e) {
        e.preventDefault();
        var name = $('#barName');
        var user = $('#userOptions');
        var imageUrl = $('#imageUrl');
        $.ajax({
            url: '/bar/create',
            method: 'POST',
            data: {
                name: name.val(),
                userId: user.val(),
                imageUrl: imageUrl.val()
            },
            success: function(response) {
                if (response) {
                    alert('Bar saved!')
                } else {
                    alert('Something failed, try again')
                }
                $('#addBarModal').modal('hide');
                name.val('');
                user.val('');
                imageUrl.val('');
                user.find('option').remove();
            }
        })
    });
});