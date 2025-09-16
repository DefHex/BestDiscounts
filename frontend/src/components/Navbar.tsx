import type {userInfoType} from "../type/UserInfoType.ts";
import type {DiscountInfoType} from "../type/DiscountInfoType.ts";
import {useEffect, useState} from "react";
import SearchBar from "./SearchBar.tsx";


interface NavbarProps {
    user: userInfoType | null,
    setUser: (user: userInfoType | null) => void,
    discounts: DiscountInfoType[],
    setDiscounts: (discounts: DiscountInfoType[]) => void,
    setFilteredDiscounts:(filteredDiscounts: DiscountInfoType[]) => void
}


export function Navbar({user, setUser, discounts, setFilteredDiscounts}: NavbarProps) {
//scroll animation
    const [prevScrollPosition, setPrevScrollPosition] = useState(0);
    const [visible, setVisible] = useState(true);
    useEffect(() => {
        function handleScroll() {
            const currentScrollPosition = window.pageYOffset;
            setVisible(prevScrollPosition > currentScrollPosition || currentScrollPosition < 10);
            setPrevScrollPosition(currentScrollPosition);
        }

        window.addEventListener("scroll", handleScroll);
        return () => window.removeEventListener("scroll", handleScroll);
    }, [prevScrollPosition]);

    function logout() {
        const host: string = window.location.host === "localhost:5173" ?
            "http://localhost:8080" :
            window.location.origin;
        window.open(host + "/logout", "_self");
        setUser(null)
    }

    return (
        <div>
            <nav style={{top: visible ? '0' : '-100px'}}>
                {user && <img src={user.avatarUrl} alt={user.userName} className={"navbar-avatar"}/>}
                <SearchBar discounts={discounts} setFilteredDiscounts={setFilteredDiscounts} />
                {user && (<button onClick={logout}>Logout</button>)}
            </nav>
        </div>
    );
}