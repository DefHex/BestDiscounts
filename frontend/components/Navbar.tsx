import axios from "axios";
import type {userInfoType} from "../type/UserInfoType.ts";

interface NavbarProps {
    user: userInfoType | null
    setUser: (user: userInfoType | null) => void
}

export function Navbar({user, setUser}: NavbarProps) {

    const loginParams = () => {
        axios.get("/api/auth/params")
            .then(response => {
                console.log(response.data);
            }).catch(e => console.error(e))
    }

    function loginWithGithub() {
        const host: string = window.location.host === "localhost:5173" ?
            "http://localhost:8080" :
            window.location.origin;
        //from video
        // window.open(host + "/login/oauth2/code/github","_self");
        //universal login page for all providers
        // window.open(host + "/login/","_self");
        //for GitHub
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

    function logout() {
        const host: string = window.location.host === "localhost:5173" ?
            "http://localhost:8080" :
            window.location.origin;
        window.open(host + "/logout", "_self");
        setUser(null)
    }

    return (
        <div>
            <button onClick={loginParams}>Login Params</button>
            {user ? (<button onClick={logout}>Logout</button>) :
                (
                    <div>
                        <button onClick={loginWithGithub}>Login with GitHub</button>
                        <button onClick={loginWithGoogle}>Login with Google</button>
                        <button onClick={loginWithFacebook}>Login with Facebook</button>
                    </div>
                )}
        </div>
    );
}