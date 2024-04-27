
import './App.css';
import {BrowserRouter as Router, Navigate as Redirect, Route, Routes as Switch} from "react-router-dom";

import ProfilePage from "./ProfilePage";
import Login from "./Login";
import OwnerHome from "./OwnerHome";
import ClientHome from './ClientHome';
import AdminHome from './AdminHome';
import Register from './Register';
import HomePage from './HomePage';

function App() {

  //const defaultRoute = window.location.pathname === "/" ? <Redirect to="/home-page"/> : undefined;
  const defaultRoute = window.location.pathname === "/" ? <Redirect to="/home-page" replace /> : null;


  return (
      <Router>
        <Switch>
          <Route exact path="/login" element={<Login/>}/>
          <Route exact path="/register"  element={<Register/>}/>

          <Route exact path="/owner-home" element={<OwnerHome/>}/>
          <Route exact path="/client-home" element={<ClientHome/>}/>
          <Route exact path="/admin-home" element={<AdminHome/>}/>

          <Route exact path="/home-page" element={<HomePage />} />
         

        </Switch>
        {defaultRoute}
      </Router>
  );
}

export default App;

