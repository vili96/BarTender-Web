var ui = new firebaseui.auth.AuthUI(firebase.auth());

// $('.firebaseui-id-submit').live('click', function() {
//     console.log('here');
//     var firstNameInput = $('.firebaseui-id-name');
//     firstNameInput.addClass('hidden');
//     firstNameInput.closest('div').addClass('hidden');
// });

ui.start('#firebaseui-auth-container', {
    callbacks: {
        signInSuccessWithAuthResult: function(authResult, redirectUrl) {
            firebase.auth().setPersistence(firebase.auth.Auth.Persistence.LOCAL);
            var user = authResult.user;
            $.ajax({
                type: 'POST',
                url: "/login",
                async: false,
                data: {
                    id: user.uid,
                    email: user.email,
                },
                dataType: "json",
            });
            return true;
        }
    },
    signInOptions: [
        {
            provider: firebase.auth.EmailAuthProvider.PROVIDER_ID,
            requireDisplayName: false
        }
    ],
    queryParameterForSignInSuccessUrl: 'signInSuccessUrl',
    signInSuccessUrl: '/dashboard',
    credentialHelper: firebaseui.auth.CredentialHelper.NONE,
});