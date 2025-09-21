import './App.css'
import axios from "axios";
import {useEffect, useState} from "react";
import {Route, Routes } from "react-router-dom";
import Dashboard from "./components/Dashboard.tsx";
import ProtectedRoute from "./components/ProtectedRoute.tsx";
import {Navbar} from "./components/Navbar.tsx";
import type {userInfoType} from "./type/UserInfoType.ts";
import type {DiscountInfoType} from "./type/DiscountInfoType.ts";
import Login from "./components/Login.tsx";
import UserPref from "./components/UserPref.tsx";


export default function App() {

    const [user,setUser]=useState<userInfoType|null>(null)
    const [discounts,setDiscounts]=useState<DiscountInfoType[]>([])
    const [filteredDiscounts,setFilteredDiscounts]=useState<DiscountInfoType[]>([])
    const [shoppingCart,setShoppingCart]=useState<string[]>([])
    const loadUser =()=>{
        axios.get("/api/auth/me")
            .then(response=>{
                console.log(response.data);
                setUser(response.data);
                DbData();
                setShoppingCart(response.data.shoppingCart);
            }).catch(()=>setUser(null))
    }
    const DbData = () => {
        axios.get("/api/data")
            .then(response => {
                console.log(response.data);
                setDiscounts(response.data);
            }).catch(e => console.error(e))
    }

    useEffect(() => {
        loadUser();
    }, []);

  return (
    <>
      <div className={"app-container"}>
          <Navbar user={user} setUser={setUser} discounts={discounts} setDiscounts={setDiscounts} setFilteredDiscounts={setFilteredDiscounts} shoppingCart={shoppingCart}/>
          {!user? <Login />:
          <Routes>
              <Route element={<ProtectedRoute user={user}/>}>
                  <Route path={"/"} element={<Dashboard user={user} discounts={discounts} filteredDiscounts={filteredDiscounts} shoppingCart={shoppingCart} setShoppingCart={setShoppingCart}/>}/>
                  <Route path={"/userPref"} element={<UserPref user={user} discounts={discounts} shoppingCart={shoppingCart} setShoppingCart={setShoppingCart}/>}/>
              </Route>
              {/*<Route path={"/logout"} element={<Dashboard user={user} discounts={discounts} filteredDiscounts={filteredDiscounts}/>}/>*/}
          </Routes>
          }
      </div>
    </>
  )
}