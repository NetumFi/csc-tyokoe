import React from 'react';

const RegistryScan = () => {
  return (
    <div>

      <h1 className="h4">Rekisteriseloste</h1>
      <p>Rekisteri- ja tietosuojaseloste
      Julkaistu 24.5.2022
        Netumin tietosuojailmoitus rekisteröidylle</p>
      <h2 className="h5">Yleistä</h2>
      <p>Tämä tietosuojailmoitus sisältää EU:n tietosuoja-asetuksen (EU) 679/2016 13 ja 14 artiklan mukaiset tiedot rekisteröidylle (luonnolliselle henkilölle). Tämä ilmoitus annetaan rekisteröidylle henkilötietoja kerättäessä.</p>
      <h2 className="h5">Rekisterinpitäjä ja rekisterinpitäjän yhteystiedot</h2>
      <address>
        <strong>Netum Oy</strong> <br />
        Yliopistonkatu 58 B<br />
        33100 TAMPERE
        </address>

      <h2 className="h5">Tietosuojavastaavan yhteystiedot</h2>
      <address>
        Erkki Esimerkki<br />
        esimerkki@netum.fi
      </address>


      <h2 className="h5">Henkilötietojen käsittelyn tarkoitukset, henkilötietoryhmät ja oikeusperusteet</h2>
      Henkilötietojen käyttötarkoitukset ovat:
      <ul>
        <li>Rekisteröityjen käyttäjien tietojen sekä kirjautumis- ja käyttölokitietojen hallinta, käyttäjätilastointi ja palvelun käytössä tarvittavien evästeiden hallinta</li>
        <li>Palvelun käyttöön ja kehittämiseen liittyvä tilastointi, analytiikka ja hallinta</li>
        <li>Palvelun rekisteröitymättömien käyttäjien käyttölokitietojen hallinta, käyttäjätilastointi sekä palvelun käytössä tarvittavien evästeiden hallinta.</li>
      </ul>
      Henkilötietojen käsittelyn oikeusperusteet:
      <ul>
        <li>Laki valtioneuvostosta (175/2003)</li>
        <li>Valtioneuvoston ohjesääntö (262/2003)</li>
        <li>Valtioneuvoston asetus opetus- ja kulttuuriministeriöstä (310/2010)</li>
      </ul>
      <h2 className="h5">Henkilötietojen käsittelijät</h2>
      Henkilötietojen käsittelijöitä ovat
      <ul>
        <li>Netum</li>
      </ul>

      <h2 className="h5">Henkilötietojen säilyttämisajat</h2>
      <p>Palveluun sisältyvät materiaalit, kommentit ja muu sisältö ja näiden käsittelyyn liittyvät henkilötiedot säilytetään ja poistetaan palvelun käyttöehtojen mukaisesti, ellei arkistointia koskevasta lainsäädännöstä tai muista säädöksistä muuta johdu.</p>
      <h2 className="h5">Rekisteröidyn oikeudet</h2>
      <p>Rekisteröidyllä on oikeus saada rekisterinpitäjältä tieto siitä, käsitelläänkö hänenhenkilötietojaan.
      Rekisteröidyllä on myös oikeus pyytää:
        <ul>
      <li>itseään koskevat henkilötiedot</li>
      <li>rekisterinpitäjää oikaisemaan tai poistamaan virheelliset tai vanhentuneet tiedot</li>
          <li>henkilötietojen käsittelyn rajoittamista</li>
          </ul>
      Mikäli rekisteröity katsoo, ettei hänen henkilötietojensa käsittely ole lainmukaista, rekisteröidyllä on oikeus tehdä asiasta valitus tietosuojavaltuutetulle.
        </p>
      <h2 className="h5">Henkilötietojen lähde/lähteet</h2>
      <p>Palvelun keräämät tiedot.</p>

      <h2 className="h5">Henkilötietojen käsittelyn suojaaminen</h2>
      <p>Netum  rekisterinpitäjänä on toteuttanut tarvittavat tekniset ja organisatoriset toimet sekä vaatii sitä myös henkilötietojen käsittelijöiltä.</p>
    </div>
  );
};

export default RegistryScan;
