import type {userInfoType} from "../type/UserInfoType.ts";

type DashboardProps = {
    user:userInfoType|null
}
export default function Dashboard(props:Readonly<DashboardProps>) {

    return (
        <div>
            <h1>Welcome {props.user?.userName}</h1>
            <img src={props.user?.avatarUrl} alt={props.user?.userName}/>
        </div>
    );
}