import './home.scss';

import React from 'react';
import {Translate} from 'react-jhipster';
import {Button} from 'reactstrap';

import {useAppSelector} from 'app/config/store';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);

  const log = () => {
    window.location.href = '/login'
  }

  return (

    <div className={'container homePage'}>
      <h1 className={'h2'}>
        <Translate contentKey="home.title">Welcome</Translate>
      </h1>
      <div className={'page-desc'}>
        <p>Voit kirjautua palveluun viidellä eri oppijaprofiililla. </p>
        <p>
          Käyttäjänimi = akseli, janika, mirka, rikhard tai roosa Salasana = user
        </p>
      </div>
      <div>
        <Button color='primary' onClick={log}>
            <Translate contentKey="global.messages.info.authenticated.link"> sign in</Translate>
        </Button>
      </div>
    </div>

  );
};

export default Home;
