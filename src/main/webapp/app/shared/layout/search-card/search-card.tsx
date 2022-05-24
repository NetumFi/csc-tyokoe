import React from 'react';
import {Badge} from "reactstrap";
import {TextFormat} from "react-jhipster";
import {APP_SUOMI_DATE_FORMAT} from "app/config/constants";
import {Link} from "react-router-dom";

const SearchCard = (props: {
  result: any,
  materialName: (m) => string,
  lang: (d) => string,
  description: (d) => string}) => {
  const aoeurl = 'https://aoe.fi/#/materiaali/';

  return <div className="card">
    <div className="row no-gutters">
      <div className="col-auto">
        {props.result.thumbnail?.filepath &&
          <img src={props.result.thumbnail?.filepath.replace(/^https:[/][/]aoe.fi/, '/aoe')} className="img-fluid" alt="img"/>}
      </div>
      <div className="col">
        <div className="card-block px-2">
            <Link to={{ pathname: aoeurl + props.result.id}} target="_blank" className={'cust-link'}>
              <h4 className="cust_card-title">
                {props.result.materialName.filter(m => m.language === props.lang).map(x => x.materialname)[0]}
              </h4>
            </Link>

          <p className="card-text">
            {props.result.description.filter(d => d.language === props.lang).map(d => d.description)[0]}
          </p>
          <div>
            {props.result.learningResourceTypes.map(t =>
              <Badge
                className={'cust_Badge'}
                key={t.learningresourcetypekey}
                pill>
                {t.value}
              </Badge>)}
            <p className={'date_text'}>
              {props.result &&
              props.result?.updatedAt ?
                <TextFormat type="date"
                            value={props.result.updatedAt}
                            format={APP_SUOMI_DATE_FORMAT} /> : null}</p>
          </div>
        </div>
      </div>
    </div>
  </div>;
}

export default SearchCard;
