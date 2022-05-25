import React, {useEffect, useState} from 'react';
import {Badge} from "reactstrap";
import {TextFormat} from "react-jhipster";
import {APP_SUOMI_DATE_FORMAT} from "app/config/constants";
import {Link} from "react-router-dom";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {createEntity, deleteEntity, getEntities} from "app/entities/reading-list/reading-list.reducer";
import {useAppDispatch, useAppSelector} from "app/config/store";

const SearchCard = (props: {
  result: any,
  component: string,
  readingListList: any,
  handleSyncList: () => void,
  lang: (d) => string}) => {
  const dispatch = useAppDispatch();
  const account = useAppSelector(state => state.authentication.account);

  const aoeurl = 'https://aoe.fi/#/materiaali/';

  const [favorite, setFavorite] = useState(false);
  const items = props.readingListList.filter(item => item.materialId === props.result.id);

  function isFavorite(): boolean {
    return items && items.length > 0;
  }

  useEffect(() => {
    dispatch(getEntities({}))
    setFavorite(isFavorite)
  }, [setFavorite]);

  const saveOrDeleteSearchMaterial = () => {
    if(isFavorite() && favorite) {
      // delete search material
      dispatch(deleteEntity(items[0].id)).then((x: any) => {
        setFavorite( x.payload.status === 204 ? false : true)
      })
    } else {
      // create search material
      const entity = {
        created: null,
        materialId: props.result.id,
        user: {
          id: account.id,
          login: account.login
        }
      }
      dispatch(createEntity(entity)).then( (x: any) => {
        setFavorite( x.payload.status === 201);
      })
    }
    props.handleSyncList();
  };

  const handleKeypress = e => {
    if (e.keyCode === 13) {
      saveOrDeleteSearchMaterial();
    }
  };

  return <div className="card">
    <div className="row no-gutters">
      <div className="col-auto">
        {props.result.thumbnail?.filepath &&
          <img src={props.result.thumbnail?.filepath.replace(/^https:[/][/]aoe.fi/, '/aoe')} className="img-fluid" alt="img"/>}
      </div>
      <div className="col">
        <div className="card-block px-4">
          <div className={'search-link-title'}>
            <Link to={{ pathname: aoeurl + props.result.id}} target="_blank" className={'cust-link'}>
              <h4 className="cust_card-title">
                {
                  props.component === 'search' ?
                    props.result.materialName.filter(m => m.language === props.lang).map(x => x.materialname)[0]
                    : props.result.name.filter(m => m.language === props.lang).map(x => x.materialname)[0]
                }
              </h4>
            </Link>
          </div>
          <div >
            <FontAwesomeIcon icon={'heart'}
                             tabIndex={0}
                             fixedWidth={true}
                             size="2x"
                             className={'fav-icon-heart'}
                             color={favorite ? '#3A7A10' : '#D3D3D3'}
                             onKeyDown={(e) => handleKeypress(e)}
                             onClick={saveOrDeleteSearchMaterial}/>
          </div>
          <div className={"cust-description"}>
            <p className="card-text">
              {props.result.description.filter(d => d.language === props.lang).map(d => d.description)[0]}
            </p>
          </div>

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
{/* props.result.thumbnail?.filepath &&
          <img src={props.result.thumbnail?.filepath} className="img-fluid" alt="img"/>*/}
