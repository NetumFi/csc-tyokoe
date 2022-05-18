export interface IEducationLevelCodeSet {
  id?: number;
  code?: string | null;
  codeId?: string | null;
  labelEn?: string | null;
  labelFi?: string | null;
  labelSv?: string | null;
}

export const defaultValue: Readonly<IEducationLevelCodeSet> = {};
