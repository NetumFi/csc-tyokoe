import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './profile.reducer';

export const ProfileDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const profileEntity = useAppSelector(state => state.profile.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="profileDetailsHeading">
          <Translate contentKey="csc2022App.profile.detail.title">Profile</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{profileEntity.id}</dd>
          <dt>
            <span id="keywords">
              <Translate contentKey="csc2022App.profile.keywords">Keywords</Translate>
            </span>
          </dt>
          <dd>{profileEntity.keywords}</dd>
          <dt>
            <span id="education">
              <Translate contentKey="csc2022App.profile.education">Education</Translate>
            </span>
          </dt>
          <dd>{profileEntity.education}</dd>
          <dt>
            <span id="language">
              <Translate contentKey="csc2022App.profile.language">Language</Translate>
            </span>
          </dt>
          <dd>{profileEntity.language}</dd>
          <dt>
            <Translate contentKey="csc2022App.profile.user">User</Translate>
          </dt>
          <dd>{profileEntity.user ? profileEntity.user.login : ''}</dd>
        </dl>
        <Button tag={Link} to="/profile" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/profile/${profileEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ProfileDetail;
