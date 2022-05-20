import './header.scss';

import React, {useState} from 'react';
import {Storage, Translate} from 'react-jhipster';
import LoadingBar from 'react-redux-loading-bar';
import {useAppDispatch} from 'app/config/store';
import {setLocale} from 'app/shared/reducers/locale';

import AdminNavbar from '../navbar/admin-navbar'
import UserNavbar from "app/shared/layout/navbar/user-navbar";

export interface IHeaderProps {
  isAuthenticated: boolean;
  isAdmin: boolean;
  ribbonEnv: string;
  isInProduction: boolean;
  isOpenAPIEnabled: boolean;
  currentLocale: string;
}

const Header = (props: IHeaderProps) => {
  const [menuOpen, setMenuOpen] = useState(false);

  const dispatch = useAppDispatch();

  const handleLocaleChange = event => {
    const langKey = event.target.value;
    Storage.session.set('locale', langKey);
    dispatch(setLocale(langKey));
    window.location.reload();
  };

  const renderDevRibbon = () =>
    props.isInProduction === false ? (
      <div className="ribbon dev">
        <a href="">
          <Translate contentKey={`global.ribbon.${props.ribbonEnv}`} />
        </a>
      </div>
    ) : null;

  const toggleMenu = () => setMenuOpen(!menuOpen);

  /* jhipster-needle-add-element-to-menu - JHipster will add new menu items here */

  return (
    <div id="app-header">
      {renderDevRibbon()}
      <LoadingBar className="loading-bar"/>
      {props.isAdmin ?
        <AdminNavbar onClick={toggleMenu}
                     open={menuOpen}
                     authenticated={props.isAuthenticated}
                     admin={props.isAdmin}
                     showOpenAPI={props.isOpenAPIEnabled}
                     currentLocale={props.currentLocale}
                     onClick1={handleLocaleChange}/>
        :
        <UserNavbar onClick={toggleMenu}
                    open={menuOpen}
                    authenticated={props.isAuthenticated}
                    admin={props.isAdmin}
                    showOpenAPI={props.isOpenAPIEnabled}
                    currentLocale={props.currentLocale}
                    onClick1={handleLocaleChange}/>
      }

    </div>
  );
};

export default Header;
