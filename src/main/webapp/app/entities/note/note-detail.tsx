import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './note.reducer';

export const NoteDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const noteEntity = useAppSelector(state => state.note.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="noteDetailsHeading">
          <Translate contentKey="csc2022App.note.detail.title">Note</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{noteEntity.id}</dd>
          <dt>
            <span id="content">
              <Translate contentKey="csc2022App.note.content">Content</Translate>
            </span>
          </dt>
          <dd>{noteEntity.content}</dd>
          <dt>
            <span id="materialId">
              <Translate contentKey="csc2022App.note.materialId">Material Id</Translate>
            </span>
          </dt>
          <dd>{noteEntity.materialId}</dd>
          <dt>
            <span id="created">
              <Translate contentKey="csc2022App.note.created">Created</Translate>
            </span>
          </dt>
          <dd>{noteEntity.created ? <TextFormat value={noteEntity.created} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="csc2022App.note.user">User</Translate>
          </dt>
          <dd>{noteEntity.user ? noteEntity.user.login : ''}</dd>
        </dl>
        <Button tag={Link} to="/note" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/note/${noteEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default NoteDetail;
