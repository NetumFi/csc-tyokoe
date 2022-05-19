import { ISearchSetting } from 'app/shared/model/search-setting.model';

export interface IEducationLevelCodeSet {
  id?: number;
  code?: string | null;
  codeId?: string | null;
  labelEn?: string | null;
  labelFi?: string | null;
  labelSv?: string | null;
  searchSettings?: ISearchSetting[] | null;
}

export const defaultValue: Readonly<IEducationLevelCodeSet> = {};
