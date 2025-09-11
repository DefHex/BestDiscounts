import './App.css'
import axios from "axios";
import {useEffect, useState} from "react";
import {Route, Routes } from "react-router-dom";
import Dashboard from "../components/Dashboard.tsx";
import ProtectedRoute from "../components/ProtectedRoute.tsx";
import {Navbar} from "../components/Navbar.tsx";
import type {userInfoType} from "../type/UserInfoType.ts";

export default function App() {

    const [user,setUser]=useState<userInfoType|null>(null)

    const loadUser =()=>{
        axios.get("/api/auth/me")
            .then(response=>{
                console.log(response.data);
                setUser(response.data)
            }).catch(()=>setUser(null))
    }

    useEffect(() => {
        loadUser()
    }, []);
  return (
    <>
      <div>
          <Navbar user={user} setUser={setUser}/>
          {/*<Login/>*/}
          <Routes>
              <Route element={<ProtectedRoute user={user}/>}>
                  <Route path={"/"} element={<Dashboard user={user}/>}/>
              </Route>
              <Route path={"/logout"} element={<Dashboard user={user}/>}/>
              <Route element={<ProtectedRoute user={user}/>}>
                  <Route path={"/dashboard"} element={<Dashboard user={user}/>}/>
              </Route>
          </Routes>
      </div>
    </>
  )
}