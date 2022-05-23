import dayjs from 'dayjs';
import { IUser } from 'app/shared/model/user.model';

export interface INote {
  id?: number;
  content?: string | null;
  materialId?: string | null;
  created?: string | null;
  user?: IUser | null;
}

export const defaultValue: Readonly<INote> = {};
