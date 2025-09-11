import {Navigate, Outlet} from "react-router-dom";
import type {userInfoType} from "../type/UserInfoType.ts";

type ProtectedRouteProps={
    user:userInfoType|null
}
export default function ProtectedRoute(props:Readonly<ProtectedRouteProps>) {
    return (
        props.user? <Outlet/>:<Navigate to={"/"}/>
    );
}