import sys, inspect
def get_classes(local_val: dict):
    classes = inspect.getmembers(sys.modules[__name__], inspect.isclass)
    classes_values = {}
    for key, value in local_val.items():
        for cls in classes:
            if isinstance(value, cls[1]):
                attr_value = {}
                for atkey in local_val[key].__dict__.keys():
                    attr_value[atkey] = local_val[key].__dict__[atkey]
                attr_value['type'] = cls[0]
                classes_values[key] = attr_value
                break
    print(classes_values)

while 1:
    x = input()
    if x == 'exit':
        break
    try:
        y = eval(x)
        if y: print(y)
    except:
        try:
            exec(x)
        except Exception as e:
            print("error: ", e)
