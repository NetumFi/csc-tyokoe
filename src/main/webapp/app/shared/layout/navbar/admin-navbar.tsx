import {Collapse, Nav, Navbar, NavbarBrand, NavbarToggler, NavItem, NavLink} from "reactstrap";
import {Home} from "app/shared/layout/header/header-components";
import {AccountMenu, AdminMenu, EntitiesMenu, LocaleMenu} from "app/shared/layout/menus";
import React from "react";


function AdminNavbar(props: {
  onClick: () => void,
  open: boolean,
  authenticated: boolean,
  admin: boolean,
  showOpenAPI: boolean,
  currentLocale: string,
  onClick1: (event) => void }) {

  return <Navbar className="bg-primary" light expand="md" fixed="top" data-cy="navbar">
    <NavbarBrand href="https://digivisio2030.fi/" >CSC2022</NavbarBrand>
    <NavbarToggler onClick={props.onClick}/>
    <Collapse isOpen={props.open} navbar>
      <Nav className="ml-auto" navbar >
        <Home/>
        {props.authenticated && <EntitiesMenu/>}
        {props.authenticated && props.admin && <AdminMenu showOpenAPI={props.showOpenAPI}/>}
      </Nav>
      <Nav >
        <LocaleMenu currentLocale={props.currentLocale} onClick={props.onClick1}/>
        <AccountMenu isAuthenticated={props.authenticated}/>
      </Nav>
    </Collapse>
  </Navbar>;
}


export default AdminNavbar;
