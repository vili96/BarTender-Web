var ui = new firebaseui.auth.AuthUI(firebase.auth());
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
    signInSuccessUrl: 'home',
    credentialHelper: firebaseui.auth.CredentialHelper.NONE,
});