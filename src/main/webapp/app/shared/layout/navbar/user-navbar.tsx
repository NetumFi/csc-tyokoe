import React from 'react';
import {Collapse, Nav, Navbar, NavbarBrand, NavbarToggler, NavItem, NavLink} from "reactstrap";
import {AccountMenu, LocaleMenu} from "app/shared/layout/menus";
import {Translate} from "react-jhipster";

const UserNavbar = (props: {
  onClick: () => void,
  open: boolean,
  authenticated: boolean,
  admin: boolean,
  showOpenAPI: boolean,
  currentLocale: string,
  onClick1: (event) => void
}) => {

  return (
    <Navbar className="bg-primary" light expand="md" fixed="top">
      <NavbarBrand href="https://digivisio2030.fi/">CSC2022</NavbarBrand>
      <NavbarToggler onClick={props.onClick}/>
      <Collapse isOpen={props.open} navbar>
        <Nav className="ml-auto" navbar>
          <NavItem>

            <NavLink href="/">
              <span>
                <Translate contentKey="global.menu.home">Home</Translate>
              </span>
            </NavLink>
          </NavItem>
          <NavItem>
            <NavLink href="https://github.com/reactstrap/reactstrap">
              <span>
                <Translate contentKey="global.menu.favorites">Favorites</Translate>
              </span>
            </NavLink>
          </NavItem>
        </Nav>
        <Nav>
          <LocaleMenu currentLocale={props.currentLocale} onClick={props.onClick1}/>
          <AccountMenu isAuthenticated={props.authenticated}/>
        </Nav>
      </Collapse>
    </Navbar>
  );

};

export default UserNavbar;
