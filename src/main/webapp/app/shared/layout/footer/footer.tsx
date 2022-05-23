import './footer.scss';

import React from 'react';
import {Translate} from 'react-jhipster';
import {Col, Row} from 'reactstrap';
import {Link} from "react-router-dom";

const Footer = () => (
  <div className="footer">

    <Row>
      <Col>
        <Link className={'cust-link-footer'} to="/registry-scan"  id="registry-scan-id" data-cy="registryScan">
          <Translate contentKey="footer.registry-scan">Accessibility</Translate>
        </Link>
      </Col>
      <Col>
        <Link to="/accessibility" id="accessibility-id" data-cy="accessibility">
          <Translate contentKey="footer.accessibility">Registry Scan</Translate>
        </Link>
      </Col>
      <Col></Col>
      <Col></Col>
      <Col></Col>
      <Col></Col>
      <Col></Col>
    </Row>
  </div>
);

export default Footer;
