function loginWithGithub() {
    const host: string = window.location.host === "localhost:5173" ?
        "http://localhost:8080" :
        window.location.origin;
    window.open(host + "/oauth2/authorization/github", "_self");
}

function loginWithGoogle() {
    const host: string = window.location.host === "localhost:5173" ?
        "http://localhost:8080" :
        window.location.origin;
    window.open(host + "/oauth2/authorization/google", "_self");
}
function loginWithFacebook() {
    const host: string = window.location.host === "localhost:5173" ?
        "http://localhost:8080" :
        window.location.origin;
    window.open(host + "/oauth2/authorization/facebook", "_self");
}

export default function Login() {
    return (
        <div>
            <h2>Please login</h2>
            <div className={"login-buttons"}>
                <button onClick={loginWithGithub}>Login with GitHub</button>
                <button onClick={loginWithGoogle}>Login with Google</button>
                <button onClick={loginWithFacebook}>Login with Facebook</button>
            </div>
        </div>
    );
}